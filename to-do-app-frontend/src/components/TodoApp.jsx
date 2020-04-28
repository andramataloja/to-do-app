import React from "react";
import "../stylesheets/TodoApp.scss";
import {
  Container,
  Grid,
  Paper,
  Typography,
  makeStyles,
} from "@material-ui/core";
import ToDoList from "./ToDoList";
import AddToDo from "./AddToDo";

const useStyles = makeStyles({
  container: {
    background: "rgba(255, 255, 255, 0.3)",
    height: "100%",
    width: "50%",
    marginTop: "40px",
  },
  heading: {
    color: "white",
    padding: "20px",
  },
  list: {
    width: "100%",
  },
});

const TodoApp = () => {
  const classes = useStyles();
  return (
    <Container className={classes.container}>
      <Grid
        container
        direction="column"
        justify="center"
        alignItems="center"
        style={{ paddingBottom: 40, paddingLeft: 10, paddingRight: 10 }}
      >
        <Grid item>
          <Typography className={classes.heading} variant="h3">
            To Do List
          </Typography>
        </Grid>
        <Paper style={{ padding: 20, marginBottom: 40 }}>
          <Grid item>
            <AddToDo />
          </Grid>
        </Paper>
        <Grid item className={classes.list}>
          <ToDoList />
        </Grid>
      </Grid>
    </Container>
  );
};

export default TodoApp;
