package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.PatientBean;
import com.rays.pro4.Model.PatientModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "PatientCtl", urlPatterns = { "/ctl/PatientCtl" })
public class PatientCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dov"))) {
			request.setAttribute("dov", PropertyReader.getValue("error.require", "dov"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require", "mobile"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("decease"))) {
			request.setAttribute("decease", PropertyReader.getValue("error.require", "decease"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		PatientBean bean = new PatientBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setMobile(DataUtility.getString(request.getParameter("mobile")));

		bean.setDov(DataUtility.getDate(request.getParameter("dov")));

		bean.setDecease(DataUtility.getString(request.getParameter("decease")));

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		PatientModel model = new PatientModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("Patient Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			PatientBean bean;

			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		PatientModel model = new PatientModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PatientBean bean = (PatientBean) populateBean(request);

			if (id > 0) {
				System.out.println("in update");
				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Patient is successfully Updated", request);
				} catch (Exception e) {
					System.out.println("Patient not update");
					e.printStackTrace();
				}
			} else {
				System.out.println("in add");
				try {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Patient is successfully Added", request);
					ServletUtility.forward(getView(), request, response);
					bean.setId(pk);
				} catch (Exception e) {
					System.out.println("Patient not added");
					e.printStackTrace();
				}
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		return ORSView.PATIENT_VIEW;
	}

}
