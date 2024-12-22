import React from 'react';
import {RenderRoutes} from './routes/routes';
import { useNavigate } from 'react-router-dom';
import { setupAxios } from './utils/axiosConfig';
function App() {
const navigate = useNavigate();
  React.useEffect(()=>{
    setupAxios(navigate);
  },[navigate]);
  return <RenderRoutes />;
}

export default App;
