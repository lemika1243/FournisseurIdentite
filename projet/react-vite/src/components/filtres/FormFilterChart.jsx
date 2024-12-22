import React, { useState } from 'react';
import { Button, Form, DatePicker} from 'antd';



const FormFilterChart = ({onFilter}) => {
  const [form_chart] = Form.useForm();

  const onFinish = (values) => {
    const { dateDebut, dateFin } = values;
    const formattedData = {
      dateDebut: dateDebut?.format('YYYY-MM-DD'),
      dateFin: dateFin?.format('YYYY-MM-DD'),
    };
    console.log('Données soumises:', formattedData);
    onFilter(formattedData);
  };

  return (
    <div>
      <Form
        form={form_chart}
        name="form_chart"
        layout="inline"
        onFinish={onFinish}
        initialValues={{
          dateDebut: null,
          dateFin: null,
        }}
      >
        {/* Date Début */}
        <Form.Item
          name="dateDebut"
          label="Date Début"
        >
          <DatePicker placeholder="Sélectionnez la date de début" format="YYYY-MM-DD" />
        </Form.Item>

        {/* Date Fin */}
        <Form.Item
          name="dateFin"
          label="Date Fin"
        >
          <DatePicker placeholder="Sélectionnez la date de fin" format="YYYY-MM-DD" />
        </Form.Item>
        {/* Submit Button */}
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Valider
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default FormFilterChart;
