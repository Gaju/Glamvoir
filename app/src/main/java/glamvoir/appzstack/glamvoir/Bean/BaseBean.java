package glamvoir.appzstack.glamvoir.Bean;

/**
 * Created by rajat on 16/12/14.
 * This is a base bean class which have the common getter
 * and setter which is used by every bean class
 */
public class BaseBean {
    private String mExceptionName = null;
    private boolean isParsed = true;
    private int successCode = 1;

    /**
     * @return the mExceptionName
     */
    public String getmExceptionName() {

        return mExceptionName;

    }

    /**
     * @param mExceptionName the mExceptionName to set
     */
    public void setmExceptionName(String mExceptionName) {

        this.mExceptionName = mExceptionName;
    }

    public boolean isParsed() {

        return isParsed;
    }

    public void setParsed(boolean isParsed)
    {

        this.isParsed = isParsed;
    }

    public int getSuccessCode() {

        return successCode;
    }

    public void setSuccessCode(int successCode) {
        this.successCode = successCode;
    }
}