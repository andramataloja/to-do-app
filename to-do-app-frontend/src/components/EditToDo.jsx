import React, { useState } from "react";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  OutlinedInput,
  FormGroup,
  Box,
} from "@material-ui/core";
import axios from "axios";
import CreateIcon from "@material-ui/icons/Create";
import { useDispatch } from "react-redux";
import { fetchTodos } from "../redux/actions";

const EditToDo = (props) => {
  const dispatch = useDispatch();
  const [open, setOpen] = useState(false);
  const [values, setValues] = useState({
    title: props.todo.title,
    description: props.todo.description,
  });

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  const handleEdit = () => {
    const data = {
      title: values.title,
      description: values.description,
      completed: props.todo.completed,
      createdAt: new Date().toISOString(),
    };

    axios
      .put(`/api/todos/${props.todo.id}`, data)
      .then(() => {
        setOpen(false);
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

  return (
    <Box>
      <CreateIcon color="primary" onClick={() => setOpen(true)} />
      <Dialog
        open={open}
        onClose={() => setOpen(false)}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Edit Todo</DialogTitle>
        <DialogContent>
          <FormGroup>
            <FormControl variant="outlined" margin="dense">
              <InputLabel htmlFor="outlined-adornment-title">Title</InputLabel>
              <OutlinedInput
                id="outlined-adornment-title"
                value={values.title}
                onChange={handleChange("title")}
                label="Title"
              />
            </FormControl>

            <FormControl variant="outlined" margin="dense">
              <InputLabel htmlFor="outlined-adornment-description">
                Description
              </InputLabel>
              <OutlinedInput
                id="outlined-adornment-description"
                value={values.description}
                onChange={handleChange("description")}
                label="Description"
              />
            </FormControl>
          </FormGroup>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpen(false)}>Cancel</Button>
          <Button onClick={handleEdit}>Save</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};
export default EditToDo;
