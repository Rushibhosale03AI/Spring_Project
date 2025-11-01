import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [tasks, setTasks] = useState([]);
  const [newTaskTitle, setNewTaskTitle] = useState('');

  // Fetch all tasks
  useEffect(() => {
    fetch('http://localhost:8080/tasks')
      .then(response => response.json())
      .then(data => setTasks(data))
      .catch(error => console.error('Error fetching tasks:', error));
  }, []);

  // Handle form submission
  const handleSubmit = (event) => {
    event.preventDefault();
    const newTask = {
      id: Date.now(), // Still a temporary ID
      title: newTaskTitle,
      completed: false
    };

    fetch('http://localhost:8080/tasks', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newTask),
    })
      .then(response => response.json())
      .then(createdTask => {
        setTasks(currentTasks => [...currentTasks, createdTask]);
        setNewTaskTitle('');
      })
      .catch(error => console.error('Error creating task:', error));
  };

  // NEW: Function to handle deleting a task
  const handleDelete = (id) => {
    // Send the DELETE request to the Spring Boot API
    fetch(`http://localhost:8080/tasks/${id}`, {
      method: 'DELETE',
    })
    .then(response => {
      // We expect a 204 No Content response
      if (response.ok) {
        // If successful, update the state to remove the task from the UI
        setTasks(currentTasks => currentTasks.filter(task => task.id !== id));
      } else {
        console.error('Failed to delete task');
      }
    })
    .catch(error => console.error('Error deleting task:', error));
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>My Task List</h1>
        
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            value={newTaskTitle}
            onChange={e => setNewTaskTitle(e.target.value)}
            placeholder="Add a new task"
          />
          <button type="submit">Add Task</button>
        </form>

        <ul>
          {tasks.map(task => (
            <li key={task.id}>
              {task.title}
              (Status: {task.completed ? 'Completed' : ' Pending   '})

              {/* NEW: Added a delete button */}
              <button onClick={() => handleDelete(task.id)} style={{ marginLeft: '10px' }}>
                Delete
              </button>
            </li>
          ))}
        </ul>
      </header>
    </div>
  );
}

export default App;