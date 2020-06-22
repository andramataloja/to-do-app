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
    marginBottom: "40px",
  },
  heading: {
    color: "white",
    padding: "20px",
  },
  list: {
    width: "100%",
  },
  listBase: {
    paddingBottom: "40px",
    paddingLeft: "10px",
    paddingRight: "10px",
  },
  addBase: {
    padding: "20px",
    marginBottom: "40px",
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
        className={classes.listBase}
      >
        <Grid item>
          <Typography className={classes.heading} variant="h3">
            To Do List
          </Typography>
        </Grid>
        <Paper className={classes.addBase}>
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
