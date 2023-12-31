import { createSlice } from '@reduxjs/toolkit';
import { PURGE } from 'redux-persist';

const initialState = {
  email: '',
  password: '',
  isLogin: false,
  member: null,
  accessToken: '',
  role : '',
};


const loginSlice = createSlice({
  name: 'login',
  initialState,
  reducers: {
    setAccessToken(state, action) {
      state.accessToken = action.payload;
    },

    setRole(state, action) {
      state.role = action.payload;
    },

    setEmail(state, action) {
      state.email = action.payload;
    },
    setPassword(state, action) {
      state.password = action.payload;
    },
    setIsLogin: (state, action) => {
      state.isLogin = action.payload;
    },
    setMember: (state, action) => {
      state.member = action.payload;
    },
    
    resetLoginForm: () => {
      return {
        email: initialState.email,
        password: initialState.password,
        // isLogin: initialState.isLogin,
        // member: initialState.member,
      };
    },
  },
  extraReducers:(builder)=>{
    builder.addCase(PURGE,()=>initialState);
  },
});

export const { setMember, setIsLogin, resetLoginForm, setEmail, setPassword, setAccessToken, setRole } = loginSlice.actions;

export default loginSlice.reducer;