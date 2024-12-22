import React, { useState } from 'react';
import CustomForm from '../../../components/forms/CustomForm';
import { Card, Col, Row } from 'antd';
import Bread from '../../../components/breadcrumb/Bread';
import BackButton from '../../../components/buttons/BackButton';

const AddPage = () =>{
    const [isLoading,setIsLoading] = useState(false);
    return (
    
        <div>
        <Bread user={'Administrateur / Salarié'} page={'Add'} />
        <Row style={{ marginBottom: '16px' }}>
            <Col>
            <BackButton to="/admin/liste" label="Retour à la liste" />
            </Col>
        </Row>
        <Row >
            <Col span={12} offset={6}>
                <Card>
                    <CustomForm />
                </Card>
            </Col>
        </Row> 
        </div>

         
    );
}
export default AddPage;