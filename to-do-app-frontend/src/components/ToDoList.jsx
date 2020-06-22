import axios from "axios";
import React, { useEffect, Fragment } from "react";
import "../stylesheets/TodoApp.scss";
import {
  Typography,
  ExpansionPanel,
  ExpansionPanelSummary,
  ExpansionPanelDetails,
  ExpansionPanelActions,
  Tooltip,
  IconButton,
  makeStyles,
  Box,
  Checkbox,
} from "@material-ui/core";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import DeleteToDo from "./DeleteToDo";
import EditToDo from "./EditToDo";
import { useSelector, useDispatch } from "react-redux";
import { fetchTodos } from "../redux/actions";

const useStyles = makeStyles({
  title: {
    alignSelf: "center",
  },
});

const ToDoList = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const todos = useSelector((state) => state.todos);

  const handleChange = (prop) => (event) => {
    const id = prop.id;
    const title = prop.title;
    const description = prop.description;
    const checked = event.target.checked;

    const data = { title: title, description: description, completed: checked };
    axios
      .put(`/api/todos/${id}`, data)
      .then(() => {
        axios
          .get("/api/todos")
          .then((res) => {
            res.data.length !== 0
              ? dispatch(fetchTodos(res.data))
              : dispatch(fetchTodos([]));
          })
          .catch((err) => console.log(err));
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    axios
      .get("/api/todos")
      .then((res) =>
        res.data.length !== 0
          ? dispatch(fetchTodos(res.data))
          : dispatch(fetchTodos([]))
      )
      .catch((err) => console.log(err));
  }, [dispatch]);

  return (
    <>
      {todos.map((todo) => (
        <Fragment key={todo.id}>
          {todo.description ? (
            <ExpansionPanel>
              <ExpansionPanelSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
              >
                <Box
                  display="flex"
                  flexDirection="row"
                  width="100%"
                  justifyContent="flex-start"
                  alignItems="center"
                >
                  <Box>
                    <Checkbox
                      checked={todo.completed}
                      onChange={handleChange(todo)}
                    />
                  </Box>
                  <Box>
                    <Typography className={classes.title}>
                      {todo.title}
                    </Typography>
                  </Box>
                </Box>
                <Box
                  display="flex"
                  flexDirection="row"
                  width="100%"
                  justifyContent="flex-end"
                >
                  <ExpansionPanelActions>
                    <Tooltip title="Edit">
                      <IconButton edge="end" aria-label="edit">
                        <EditToDo todo={todo} />
                      </IconButton>
                    </Tooltip>
                    <Tooltip title="Delete">
                      <IconButton edge="end" aria-label="delete">
                        <DeleteToDo id={todo.id} />
                      </IconButton>
                    </Tooltip>
                  </ExpansionPanelActions>
                </Box>
              </ExpansionPanelSummary>
              <ExpansionPanelDetails>
                <Typography>{todo.description}</Typography>
              </ExpansionPanelDetails>
            </ExpansionPanel>
          ) : (
            <ExpansionPanel key={todo.id} expanded={false}>
              <ExpansionPanelSummary
                aria-controls="panel1a-content"
                id="panel1a-header"
              >
                <Box
                  display="flex"
                  flexDirection="row"
                  width="100%"
                  justifyContent="flex-start"
                  alignItems="center"
                >
                  <Box>
                    <Checkbox
                      checked={todo.completed}
                      onChange={handleChange(todo)}
                    />
                  </Box>
                  <Box>
                    <Typography className={classes.title}>
                      {todo.title}
                    </Typography>
                  </Box>
                </Box>
                <Box
                  display="flex"
                  flexDirection="row"
                  width="100%"
                  justifyContent="flex-end"
                >
                  <ExpansionPanelActions>
                    <Tooltip title="Edit">
                      <IconButton edge="end" aria-label="edit">
                        <EditToDo todo={todo} />
                      </IconButton>
                    </Tooltip>
                    <Tooltip title="Delete">
                      <IconButton edge="end" aria-label="delete">
                        <DeleteToDo id={todo.id} />
                      </IconButton>
                    </Tooltip>
                  </ExpansionPanelActions>
                </Box>
              </ExpansionPanelSummary>
            </ExpansionPanel>
          )}
        </Fragment>
      ))}
    </>
  );
};

export default ToDoList;
