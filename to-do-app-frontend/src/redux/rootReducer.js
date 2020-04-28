export const initialState = {
  todos: [],
};

export const rootReducer = (state = initialState, action) => {
  switch (action.type) {
    case "FETCH_TODOS":
      return {
        ...state,
        todos: action.payload,
      };
    default:
      return state;
  }
};
export default rootReducer;
