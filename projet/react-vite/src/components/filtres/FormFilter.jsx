import React, { useState } from 'react';
import { Button, Form, DatePicker, Input, InputNumber, Select } from 'antd';

const { Option } = Select;

const FormFilter = () => {
  const [form_multi_criteria] = Form.useForm();

  const onFinish = (values) => {
    const { dateDebut, dateFin, minValue, maxValue, searchString, selectedOption } = values;

    console.log('Données soumises:', {
      dateDebut: dateDebut?.format('YYYY-MM-DD'),
      dateFin: dateFin?.format('YYYY-MM-DD'),
      minValue,
      maxValue,
      searchString,
      selectedOption,
    });
  };

  return (
    <div>
      <Form
        form={form_multi_criteria}
        name="form_multi_criteria"
        layout="inline"
        onFinish={onFinish}
        initialValues={{
          dateDebut: null,
          dateFin: null,
          minValue: null,
          maxValue: null,
          searchString: '',
          selectedOption: '',
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

        {/* Min Value */}
        <Form.Item
          name="minValue"
          label="Valeur Min"
        >
          <InputNumber placeholder="Min" min={0} />
        </Form.Item>

        {/* Max Value */}
        <Form.Item
          name="maxValue"
          label="Valeur Max"
        >
          <InputNumber placeholder="Max" min={0} />
        </Form.Item>

        {/* Input String */}
        <Form.Item
          name="searchString"
          label="Recherche Texte"
        >
          <Input placeholder="Entrez un mot clé" />
        </Form.Item>

        {/* Select */}
        <Form.Item
          name="selectedOption"
          label="Option"
        >
          <Select placeholder="Choisissez une option">
            <Option value="option1">Option 1</Option>
            <Option value="option2">Option 2</Option>
            <Option value="option3">Option 3</Option>
          </Select>
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

export default FormFilter;
