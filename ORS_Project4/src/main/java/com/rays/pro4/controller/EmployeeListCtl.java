package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Model.EmployeeModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "EmployeeListCtl" , urlPatterns = {"/ctl/EmployeeListCtl"})
public class EmployeeListCtl extends BaseCtl {

	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		EmployeeBean bean = new EmployeeBean();
		bean.setFullName(DataUtility.getString(request.getParameter("fullName")));
		bean.setBirthDate(DataUtility.getDate(request.getParameter("birthDate")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<EmployeeBean> list = null;
		List<EmployeeBean> next = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		EmployeeBean bean = (EmployeeBean) populateBean(request);

		EmployeeModel model = new EmployeeModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			request.setAttribute("nextListSize", (next != null) ? next.size() : 0);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<EmployeeBean> list = null;
		List<EmployeeBean> next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		EmployeeBean bean = (EmployeeBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		EmployeeModel model = new EmployeeModel();
		try {
		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.EMPLOYEE_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					
						model.delete(DataUtility.getLong(id));
				}
				ServletUtility.setSuccessMessage("Data is deleted successfully", request);
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);
			return;
		}

		ServletUtility.setBean(bean, request);

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (!OP_DELETE.equalsIgnoreCase(op) && (list == null || list.size() == 0)) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			request.setAttribute("nextListSize", next.size());
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.EMPLOYEE_LIST_VIEW;
	}
}