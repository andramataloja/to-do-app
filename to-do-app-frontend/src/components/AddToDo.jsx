import axios from "axios";
import React, { useState } from "react";
import { TextField, Grid, Fab } from "@material-ui/core";
import AddIcon from "@material-ui/icons/Add";
import { useDispatch } from "react-redux";
import { fetchTodos } from "../redux/actions";

const AddToDo = () => {
  const dispatch = useDispatch();
  const [todo, setTodo] = useState({
    title: "",
    description: "",
  });

  const clearFields = () => {
    setTodo({ title: "", description: "" });
  };

  const handleChange = (prop) => (event) => {
    setTodo({ ...todo, [prop]: event.target.value });
  };

  const handleSubmit = () => {
    const data = {
      title: todo.title,
      description: todo.description,
    };
    {
      todo.title &&
        axios
          .post("/api/todos", data)
          .then(() => {
            clearFields();
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
    }
  };

  return (
    <form>
      <Grid container direction="column">
        <Grid item>
          <TextField
            id="titleinput"
            label="*Thing to do"
            variant="outlined"
            margin="dense"
            value={todo.title}
            onChange={handleChange("title")}
          />
        </Grid>
        <Grid item>
          <TextField
            id="description-textarea"
            label="Add description"
            placeholder="Add description"
            multiline
            variant="outlined"
            value={todo.description}
            onChange={handleChange("description")}
          />
        </Grid>
        <Grid
          container
          justify="flex-end"
          alignItems="flex-end"
          style={{ marginTop: 12 }}
        >
          <Fab aria-label="add" size="small" color="secondary">
            <AddIcon onClick={() => handleSubmit()} />
          </Fab>
        </Grid>
      </Grid>
    </form>
  );
};

export default AddToDo;
