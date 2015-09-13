package glamvoir.appzstack.glamvoir.model.net.response;

/**
 * Created by michal.luszczuk on 2014-07-11.
 */

interface ResponseStatusExtra {
    public static final String STATUS_SUCCESS = "0";
    public static final String STATUS_ERROR = "error";
}

public enum ResponseStatus implements ResponseStatusExtra {
    Success(STATUS_SUCCESS), Error(STATUS_ERROR);

    private String value;

    private ResponseStatus(String name) {
        this.value = name;
    }

    public static ResponseStatus fromName(String name) {
        if(name == null)
        {
            return null;
        }

        if (name.equals(STATUS_SUCCESS)) {
            return Success;
        } else if (!name.equals(STATUS_SUCCESS)) {
            return Error;
        } else {
            return null;
        }
    }

    public String getName() {
        return value;
    }
}
