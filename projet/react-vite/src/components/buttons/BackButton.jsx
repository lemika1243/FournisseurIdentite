import React from 'react';
import { Button } from 'antd';
import { ArrowLeftOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';

const BackButton = ({ to, label = "Retour" }) => {
  const navigate = useNavigate();

  const handleBack = () => {
    if (to) {
      navigate(to);
    } else {
      navigate(-1); 
    }
  };

  return (
    <Button
      onClick={handleBack}
      icon={<ArrowLeftOutlined />}
      style={{
        fontSize: '16px',
        padding: '8px 16px',
        border: '2px solid #1890ff', 
        borderRadius: '8px', 
        backgroundColor: '#f0f8ff', 
        color: '#1890ff', 
        fontWeight: 'bold', 
        display: 'flex',
        alignItems: 'center',
        gap: '8px', 
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', 
        transition: 'all 0.3s ease',
      }}
      onMouseEnter={(e) =>
        (e.target.style.backgroundColor = '#e6f7ff') 
      }
      onMouseLeave={(e) =>
        (e.target.style.backgroundColor = '#f0f8ff') 
      }
    >
      {label}
    </Button>
  );
};

export default BackButton;
