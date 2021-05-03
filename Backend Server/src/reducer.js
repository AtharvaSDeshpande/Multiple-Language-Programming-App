export const initialState = {
    user: null,
};                      // Initial state, when the app is started 

export const actionTypes = {
    SET_USER: "SET_USER",
};                          //Information is pushed into the data layer

const reducer = (state,action) => {
    console.log (action);
    switch(action.type) {
        case actionTypes.SET_USER:
            return  {
                            ...state,
                            user: action.user,          //  Change the user to what we dispatched
                    };
    default:
        return state;
    }
};
export default reducer;
