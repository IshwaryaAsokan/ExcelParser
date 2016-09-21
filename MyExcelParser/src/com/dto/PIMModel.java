package com.dto;

import com.excel.data.ExcelColumn;
import com.excel.data.ExcelObj;

@ExcelObj()
public class PIMModel {

	@ExcelColumn(colName = "parent" , colNameAlias = {"item_no"})
	private String parent;

	@ExcelColumn(colName = "child" , colNameAlias = {"cs child", "CS_Item_No"})
	private String child;

	@ExcelColumn(colName = "CS Type" , colNameAlias = {"Group_Type","Cross_Sell_Type_Id"})
	private String csType;
	

	@ExcelColumn(colName = "status Id" , colNameAlias = "status_id")
	private int statusId;
	
	@ExcelColumn(colName = "action" , colNameAlias = "action")
	private String action;

	public PIMModel(){
		
	}
	public PIMModel(String parent, String child, String itemType,
			String csType, String csChild, String action) {
		super();
		this.parent = parent;
		this.child = child;
		this.csType = csType;
	//	this.csChild = csChild;
		this.action = action;
	}
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getCSType() {
		return csType;
	}

	public void setCSType(String csType) {
		this.csType = csType;
	}
/*
	public String getCsChild() {
		return csChild;
	}

	public void setCsChild(String csChild) {
		this.csChild = csChild;
	}
*/
	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}

	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		//result = prime * result + ((csChild == null) ? 0 : csChild.hashCode());
		result = prime * result + ((csType == null) ? 0 : csType.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PIMModel other = (PIMModel) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		/*if (csChild == null) {
			if (other.csChild != null)
				return false;
		} else if (!csChild.equals(other.csChild))
			return false;
		*/if (csType == null) {
			if (other.csType != null)
				return false;
		} else if (!csType.equals(other.csType))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

	

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override //TO DO
	public String toString() {
		return "PIMModel [parent=" + parent + ", child=" + child
				+ ", csType=" + csType
				+ ", statusId=" + statusId 
						+ ", action=" + action + "]";
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}


}
