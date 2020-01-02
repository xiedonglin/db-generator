package ${packagename}.model;


import java.io.Serializable;

public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sortorder;

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sortorder == null) ? 0 : sortorder.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BaseModel other = (BaseModel)obj;

        if (sortorder == null) {
            if (other.sortorder != null) {
                return false;
            }
        } else if (!sortorder.equals(other.sortorder)) {
            return false;
        }
        return true;
    }
}