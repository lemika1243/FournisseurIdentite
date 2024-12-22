import React from 'react';
import {Route, Routes } from 'react-router-dom';
import Loader from '../components/loader/loaderOne';
import LandingPage from '../pages/landing/LandingPage';
import LayoutParentClient from '../components/display/LayoutClient';
import LayoutParentAdmin  from '../components/display/LayoutAdmin';
import DashboardPageClient from '../pages/client/dashboard/DashboardPage';
import DashboardPageAdmin from '../pages/admin/dashboard/DashboardPage';
import ListPage from '../pages/admin/liste/ListPage';
import AddPage from '../pages/admin/liste/AddPage';
import ListPage2 from '../pages/admin/liste/ListPage2';




const routes = [
  {
    path: '/',
    element: <LandingPage />,
  },
  {
    path: '/client',
    element: <LayoutParentClient />,
    children: [
      {
        path: 'dashboard', 
        element: <DashboardPageClient/>,
      },
      {
        path: 'paiement',
        element: <div>Paiement</div>,
      },
    ],
  },
  {
    path: '/admin',
    element: <LayoutParentAdmin />,
    children: [
      {
        path: 'dashboard', 
        element: <DashboardPageAdmin/>,
      },
      {
        path: 'liste',
        element: <ListPage />,
      },
      {
        path: 'liste2',
        element: <ListPage2 />,
      },
      {
        path:'entity/add',
        element:<AddPage />,
      }
    ],
  },
];



const RenderRoutes = () => {
  const renderRoute = (routes) =>
    routes.map((route, index) => (
      <Route key={index} path={route.path} element={route.element}>
        {route.children && renderRoute(route.children)}
      </Route>
    ));

  return <Routes>{renderRoute(routes)}</Routes>;
};

export { routes, RenderRoutes };
