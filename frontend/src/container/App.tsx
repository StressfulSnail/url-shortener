import React, { Component } from 'react';
import './App.css';
import { Button } from "@material-ui/core";

class App extends Component {
  render() {
    return (
        <Button variant="contained" color="primary">
            Hello World
        </Button>
    );
  }
}

export default App;
