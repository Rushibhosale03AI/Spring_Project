import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [tasks, setTasks] = useState([]);
  const [newTaskTitle, setNewTaskTitle] = useState('');

  // Fetch all tasks (no change)
  useEffect(() => {
    fetch('http://localhost:8080/tasks')
      .then(response => response.json())
      .then(data => setTasks(data))
      .catch(error => console.error('Error fetching tasks:', error));
  }, []);

  // Handle form submission (no change)
  const handleSubmit = (event) => {
    event.preventDefault();
    const newTask = {
      id: Date.now(),
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

  // Handle delete (no change)
  const handleDelete = (id) => {
    fetch(`http://localhost:8080/tasks/${id}`, {
      method: 'DELETE',
    })
    .then(response => {
      if (response.ok) {
        setTasks(currentTasks => currentTasks.filter(task => task.id !== id));
      } else {
        console.error('Failed to delete task');
      }
    })
    .catch(error => console.error('Error deleting task:', error));
  };

  // NEW: Function to handle toggling the 'completed' status
  const handleToggleComplete = (id) => {
    // 1. Find the task we need to update
    const taskToToggle = tasks.find(task => task.id === id);
    
    // 2. Create the new "updated" task object
    const updatedTask = { 
      ...taskToToggle, // Copy all old fields
      completed: !taskToToggle.completed // Flip the 'completed' value
    };

    // 3. Send the PUT request
    fetch(`http://localhost:8080/tasks/${id}`, {
      method: 'PUT', // Use the PUT method
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updatedTask), // Send the entire updated task
    })
    .then(response => response.json())
    .then(returnedTask => {
      // 4. Update our React state with the new task
      setTasks(currentTasks => 
        currentTasks.map(task => 
          task.id === id ? returnedTask : task // Find the task and replace it
        )
      );
    })
    .catch(error => console.error('Error updating task:', error));
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>My Task List</h1>
        
        <form onSubmit={handleSubmit}>
          {/* ... (form is the same) ... */}
          <input
            type="text"
            value={newTaskTitle}
            onChange={e => setNewTaskTitle(e.target.value)}
            placeholder="Add a new task"
          />
          <button type="submit">Add Task</button>
        </form>

        <ul>
          {/* UPDATED: Changed the list rendering */}
          {tasks.map(task => (
            <li key={task.id} className={task.completed ? 'completed-task' : ''}>
              <input 
                type="checkbox"
                checked={task.completed}
                onChange={() => handleToggleComplete(task.id)}
              />
              {task.title}
              
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

// (Optional: To make completed tasks look different, add this to src/App.css)
/*
.completed-task {
  text-decoration: line-through;
  opacity: 0.7;
}
*/

export default App;