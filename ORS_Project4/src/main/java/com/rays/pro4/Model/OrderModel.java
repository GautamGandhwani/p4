package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Util.JDBCDataSource;

public class OrderModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_order");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(OrderBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_order values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getProductName());
		pstmt.setDate(3, new java.sql.Date(bean.getOrderDate().getTime()));
		pstmt.setString(4, bean.getQuantity());
		pstmt.setString(5, bean.getCustomer());

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(OrderBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_order where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(OrderBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_order set productName = ?, orderDate = ?, quantity = ?, customer = ? where id = ?");

		pstmt.setString(1, bean.getProductName());
		pstmt.setDate(2, new java.sql.Date(bean.getOrderDate().getTime()));
		pstmt.setString(3, bean.getQuantity());
		pstmt.setString(4, bean.getCustomer());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("product update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public OrderBean findByPK(long pk) throws Exception {

		String sql = "select * from st_order where id = ?";
		OrderBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new OrderBean();
			bean.setId(rs.getLong(1));
			bean.setProductName(rs.getString(2));
			bean.setOrderDate(rs.getDate(3));
			bean.setQuantity(rs.getString(4));
		}

		rs.close();

		return bean;
	}

	public List search(OrderBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_order where 1=1");
		if (bean != null) {

			if (bean.getProductName() != null && bean.getProductName().length() > 0) {
				sql.append(" AND productName like '" + bean.getProductName() + "%'");
			}

			if (bean.getQuantity() != null && bean.getQuantity().length() > 0) {
				sql.append(" AND quntity like '" + bean.getQuantity() + "%'");
			}

			if (bean.getCustomer() != null && bean.getCustomer().length() > 0) {
				sql.append(" AND customer like '" + bean.getCustomer() + "%'");
			}

			if (bean.getOrderDate() != null && bean.getOrderDate().getTime() > 0) {
				Date d = new Date(bean.getOrderDate().getTime());
				sql.append(" AND orderDate = '" + d + "'");
				System.out.println("done");
			}

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);

		}

		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new OrderBean();
			bean.setId(rs.getLong(1));
			bean.setProductName(rs.getString(2));
			bean.setOrderDate(rs.getDate(3));
			bean.setQuantity(rs.getString(4));
			bean.setCustomer(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_order");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			OrderBean bean = new OrderBean();

			bean.setId(rs.getLong(1));
			bean.setProductName(rs.getString(2));
			bean.setOrderDate(rs.getDate(3));
			bean.setQuantity(rs.getString(4));
			bean.setCustomer(rs.getString(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}

}
