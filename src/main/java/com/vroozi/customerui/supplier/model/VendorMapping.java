package com.vroozi.customerui.supplier.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: rashidha
 * Date: 9/26/12
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorMapping implements Serializable {

    private static final long serialVersionUID = -2880574978732780594L;
    private Long row;
    private int unitId;
    private String unspscCode;
    private String vendorName;
    private String vendorDuns;
    private String vendorNumber;
    private String createdOn;
	private String active;
    private int supplierId;
    private String uniqueSupplierId;
    private String uniqueSystemId;
    private String comments;
    private String description;
    private boolean display;
    
    
    
    public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
    public int getUnitId() {
        return unitId;
    }
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
//        String startUNSPSC;
//        String endUNSPSC;

    public String getUnspscCode() {
        return unspscCode;
    }
    public void setUnspscCode(String unspscCode) {
        this.unspscCode = unspscCode;
    }
    //        public String getCustomCode() {
//            return customCode;
//        }
//        public void setCustomCode(String customCode) {
//            this.customCode = customCode;
//        }
//        public String getCustomLabel() {
//            return customLabel;
//        }
//        public void setCustomLabel(String customLabel) {
//            this.customLabel = customLabel;
//        }
    public String getVendorNumber() {
        return vendorNumber;
    }
    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }
    //        public String getParent() {
//            return parent;
//        }
//        public void setParent(String parent) {
//            this.parent = parent;
//        }
    /*	public String getLevel1Val() {
            return level1Val;
        }
        public void setLevel1Val(String level1Val) {
            this.level1Val = level1Val;
        }
        public String getLevel2Val() {
            return level2Val;
        }
        public void setLevel2Val(String level2Val) {
            this.level2Val = level2Val;
        }
        public String getLevel3Val() {
            return level3Val;
        }
        public void setLevel3Val(String level3Val) {
            this.level3Val = level3Val;
        }
    *//*	public boolean isLevel1() {
		return level1;
	}
	public void setLevel1(boolean level1) {
		this.level1 = level1;
	}
	public boolean isLevel2() {
		return level2;
	}
	public void setLevel2(boolean level2) {
		this.level2 = level2;
	}
	public boolean isLevel3() {
		return level3;
	}
	public void setLevel3(boolean level3) {
		this.level3 = level3;
	}*/
    public Long getRow() {
        return row;
    }
    public void setRow(Long row) {
        this.row = row;
    }
//        public String getStartUNSPSC() {
//            return startUNSPSC;
//        }
//        public void setStartUNSPSC(String startUNSPSC) {
//            this.startUNSPSC = startUNSPSC;
//        }
//        public String getEndUNSPSC() {
//            return endUNSPSC;
//        }
//        public void setEndUNSPSC(String endUNSPSC) {
//            this.endUNSPSC = endUNSPSC;
//        }


    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorDuns() {
        return vendorDuns;
    }

    public void setVendorDuns(String vendorDuns) {
        this.vendorDuns = vendorDuns;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getUniqueSupplierId() {
        return uniqueSupplierId;
    }

    public void setUniqueSupplierId(String uniqueSupplierId) {
        this.uniqueSupplierId = uniqueSupplierId;
    }
    
    public String getUniqueSystemId() {
        return uniqueSystemId;
    }
    
    public void setUniqueSystemId(String uniqueSystemId) {
        this.uniqueSystemId = uniqueSystemId;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
}

