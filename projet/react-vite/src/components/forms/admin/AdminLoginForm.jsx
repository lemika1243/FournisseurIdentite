import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { MailOutlined, LockOutlined } from '@ant-design/icons';
import { loginAdmin } from '../../../services/admin.service';
import EmailPinForm from './EmailPinForm'; // Import du composant EmailPinForm

const AdminLoginForm = () => {
  const [loading, setLoading] = useState(false);
  const [email, setEmail] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false); // Flag pour afficher EmailPinForm

  const handleSubmit = () => {
    // Le formulaire a été soumis
  };

  const onFinish = async (values) => {
    setLoading(true);
    try {
      const payload = { email: values.email, password: values.password };
      // const data = await loginAdmin(payload);
      // localStorage.setItem('token', data.token);
      // localStorage.setItem('user', JSON.stringify(data.user));
      setEmail(values.email); // Stocker l'email dans le state
      setIsLoggedIn(true); // L'utilisateur est connecté, afficher EmailPinForm
      message.success('Connexion réussie');
    } catch (error) {
      message.error('Erreur de connexion');
    } finally {
      setLoading(false);
    }
  };

  // Affiche EmailPinForm après une connexion réussie
  return (
    <div>
      {!isLoggedIn ? (
        <Form layout="vertical" onFinish={onFinish}>
          <Form.Item
            label="Email"
            name="email"
            rules={[
              {
                required: true,
                message: 'Veuillez saisir votre email',
              },
              {
                type: 'email',
                message: 'Veuillez entrer un email valide',
              },
            ]}
          >
            <Input prefix={<MailOutlined />} placeholder="Entrer votre email" />
          </Form.Item>

          <Form.Item
            label="Mot de passe"
            name="password"
            rules={[
              {
                required: true,
                message: 'Veuillez saisir votre mot de passe',
              },
              {
                min: 4,
                message: 'Le mot de passe doit contenir au moins 4 caractères',
              },
            ]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="Entrer votre mot de passe"
            />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" block loading={loading}>
              Se connecter
            </Button>
          </Form.Item>
        </Form>
      ) : (
        <EmailPinForm email={email} /> // Afficher EmailPinForm si l'utilisateur est connecté
      )}
    </div>
  );
};

export default AdminLoginForm;
