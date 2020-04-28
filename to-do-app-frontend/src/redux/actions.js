export const fetchTodos = (data) => {
  return {
    type: "FETCH_TODOS",
    payload: data,
  };
};
