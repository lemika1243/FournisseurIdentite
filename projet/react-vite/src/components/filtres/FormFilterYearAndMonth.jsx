import React from 'react';
import { Button, Form, DatePicker } from 'antd';

const FormFilterYearAndMonth = ({ onFilter }) => {
  const [form_month_year] = Form.useForm();

  const onFinish = (values) => {
    const formattedData = {
      year: values.year ? values.year.format('YYYY') : null, 
      month: values.month ? values.month.format('MM') : null, 
    };

    console.log('Données soumises', formattedData);
    onFilter(formattedData); 
  };

  return (
    <div>
      <Form
        form={form_month_year}
        name="form_month_year"
        layout="inline"
        onFinish={onFinish}
      >
        {/* Sélection de l'année */}
        <Form.Item
          name="year"
          label="Année"
        >
          <DatePicker
            picker="year"
            placeholder="Sélectionnez une année"
          />
        </Form.Item>

        {/* Sélection du mois */}
        <Form.Item
          name="month"
          label="Mois"
        >
          <DatePicker
            picker="month"
            placeholder="Sélectionnez un mois"
          />
        </Form.Item>

        {/* Bouton de validation */}
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Valider
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default FormFilterYearAndMonth;
