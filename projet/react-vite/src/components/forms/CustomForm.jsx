import React from "react";
import { Form, Input, Select, Radio, Checkbox, InputNumber, Button } from "antd";

const { Option } = Select;

const CustomForm = () => {
  const [custom_form] = Form.useForm();

  const handleFinish = (values) => {
    console.log("Données soumises :", values);
  };

  return (
    <Form
      form={custom_form}
      layout="vertical"
      onFinish={handleFinish}
      initialValues={{
        radioOption: "option1",
        checkboxOption: ["option1"],
      }}
    >
      {/* Input Text */}
      <Form.Item
        label="Nom"
        name="name"
        rules={[
          {
            required: true,
            message: "Veuillez entrer votre nom !",
          },
        ]}
      >
        <Input placeholder="Entrez votre nom" />
      </Form.Item>

      {/* Select Option */}
      <Form.Item
        label="Catégorie"
        name="category"
        rules={[
          {
            required: true,
            message: "Veuillez sélectionner une catégorie !",
          },
        ]}
      >
        <Select placeholder="Choisissez une catégorie">
          <Option value="cat1">Catégorie 1</Option>
          <Option value="cat2">Catégorie 2</Option>
          <Option value="cat3">Catégorie 3</Option>
        </Select>
      </Form.Item>

      {/* Radio Group */}
      <Form.Item
        label="Type"
        name="radioOption"
        rules={[
          {
            required: true,
            message: "Veuillez sélectionner une option !",
          },
        ]}
      >
        <Radio.Group>
          <Radio value="option1">Option 1</Radio>
          <Radio value="option2">Option 2</Radio>
          <Radio value="option3">Option 3</Radio>
        </Radio.Group>
      </Form.Item>

      {/* Checkbox Group */}
      <Form.Item
        label="Options supplémentaires"
        name="checkboxOption"
        rules={[
          {
            required: true,
            message: "Veuillez sélectionner au moins une option !",
          },
        ]}
      >
        <Checkbox.Group>
          <Checkbox value="option1">Option 1</Checkbox>
          <Checkbox value="option2">Option 2</Checkbox>
          <Checkbox value="option3">Option 3</Checkbox>
        </Checkbox.Group>
      </Form.Item>

      {/* Input Number */}
      <Form.Item
        label="Quantité"
        name="quantity"
        rules={[
          {
            required: true,
            type: "number",
            min: 1,
            message: "Veuillez entrer une quantité valide !",
          },
        ]}
      >
        <InputNumber min={1} max={100} placeholder="Entrez une quantité" />
      </Form.Item>

      {/* Submit Button */}
      <Form.Item>
        <Button type="primary" htmlType="submit">
          Valider
        </Button>
      </Form.Item>
    </Form>
  );
};

export default CustomForm;
