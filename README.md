
# Cafe Management System

The Cafe Management System is a web application designed to streamline the operations of cafes by managing records, billing, and customer data efficiently. This system is developed using Angular for the frontend, Spring boot for the backend, and MySQL for the database.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)

## Introduction

The Cafe Management System helps cafe owners maintain records digitally, allowing for efficient and quick billing processes. The system stores data in a database, enabling cafe owners to add products, manage customer orders, and generate bills with ease. This system replaces the traditional manual methods, reducing the risk of data loss and redundancy.

## Features

- Product Management: Add, update, and delete products in the database.
- Customer Management: Store and manage customer information.
- Billing System: Generate bills quickly and store them for future reference.
- Sales Monitoring: Track daily sales and generate various reports.

## Technologies Used

- **Frontend**: Angular
- **Backend**: spring boot
- **Database**: MySQL

## Installation

### Prerequisites

- spring boot and npm installed
- MySQL server installed and running


## Usage

The backend server will be running on \`http://localhost:8080\`. You can use tools like Postman to test the API endpoints.

## API Endpoints

### Product Management

- **GET /products**: Retrieve all products
- **POST /products**: Add a new product
- **PUT /products/:id**: Update a product
- **DELETE /products/:id**: Delete a product

### Customer Management

- **GET /customers**: Retrieve all customers
- **POST /customers**: Add a new customer
- **PUT /customers/:id**: Update a customer
- **DELETE /customers/:id**: Delete a customer

### Billing

- **POST /bills**: Generate a new bill
- **GET /bills/:id**: Retrieve a specific bill
- **GET /bills**: Retrieve all bills

### Sales Monitoring

- **GET /sales/daily**: Get daily sales report
- **GET /sales/monthly**: Get monthly sales report