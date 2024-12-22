import React,{useState} from 'react';
import { Segmented } from 'antd';
import AdminLoginForm from './AdminLoginForm';
import InscriptionForm from '../InscriptionForm';
const AdminForm = ()=>{
    const [selectedOption, setSelectedOption] = useState('Login');
    return(
    <div>
        <Segmented 
        options={['Login','Register']}
        onChange={(value) =>{
            setSelectedOption(value);
        }}
        defaultValue="Login"
        />
        <div style={{ marginTop: '20px' }}>
        {selectedOption === 'Login' && <AdminLoginForm />}
        {selectedOption === 'Register' && <InscriptionForm />}
      </div>
    </div>
    );

}
export default AdminForm;