import React, { useState } from "react";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Box,
} from "@material-ui/core";
import DeleteIcon from "@material-ui/icons/Delete";
import axios from "axios";
import { useDispatch } from "react-redux";
import { fetchTodos } from "../redux/actions";

const DeleteToDo = (props) => {
  const [open, setOpen] = useState(false);
  const dispatch = useDispatch();

  const handleDelete = () => {
    axios
      .delete(`/api/todos/${props.id}`)
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
      <DeleteIcon color="primary" onClick={() => setOpen(true)} />
      <Dialog
        open={open}
        onClose={() => setOpen(false)}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
        data-testid="delete-dialog"
      >
        <DialogTitle id="alert-dialog-title">Delete?</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Are you sure you want to delete this todo?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpen(false)}>Cancel</Button>
          <Button onClick={() => handleDelete()}>Delete</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};
export default DeleteToDo;
