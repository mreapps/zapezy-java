package com.mreapps.zapezy.web.bean.social;

@SuppressWarnings("UnusedDeclaration")
public class FacebookError
{
    private Error error;

    public FacebookError(Error error)
    {
        this.error = error;
    }

    public Error getError()
    {
        return error;
    }

    public void setError(Error error)
    {
        this.error = error;
    }

    public class Error
    {
        private String message;
        private String type;
        private String code;

        public Error(String message, String type, String code)
        {
            this.message = message;
            this.type = type;
            this.code = code;
        }

        public Error()
        {
        }

        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }
    }
}
