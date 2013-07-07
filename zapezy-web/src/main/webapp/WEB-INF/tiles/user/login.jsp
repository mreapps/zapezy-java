<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
    #login_form {
        width: 320px;
    }

    #login_form label {
        display: inline-block;
        min-width: 110px;
        text-align: right;
    }

    #login_form input {
        width: 200px;
        margin-bottom: 5px;
    }

    #login_form a {
        float: right;
    }

    #sign_in {
        margin-top: 10px;
        width: 100px !important;
        float: right;
    }

    #fbLoginBtn {
        border: 1px solid #FF0000;
        display: block;
        width: 210px;
        height: 25px;
        margin: 0;
        padding: 0;
    }

    #fbLoginBtn img {
        display: inline-block;
        padding: 0;
        margin: 0;
        height: 100%;
    }

    #fbLoginBtn span {
        display: inline-block;
        background: #00FF00;
    }

    #fb_link {
        height: 26px;
        width: 210px;
        padding: 0;
        margin: 0;
        border: 1px solid #FF0000;
    }

    #fb_link img {
        height: 30px;
        display: block;
        float: left;
        position: relative;
        top: -2px;
        left: -2px;
    }

    #fb_link span {
        position: relative;
        left: -5px;
        display: block;
        float: right;
        background: none repeat scroll 0 0 #3B5999;
        color: #FFFFFF;
        width: 170px;
        margin: 0;
        height: 27px;
        padding-left: 10px;
        line-height: 30px;
        font-weight: bold;
    }

</style>

<script type="text/javascript">
    $(function () {
        $(".accordion").accordion({
            heightStyle: "content"
        });
    });
</script>
<div style="height: 20px"></div>
<div class="accordion">
    <h3>Sign in</h3>

    <div style="height: auto">
        <form id="login_form" name="f" action="<c:url value='j_spring_security_check' />" method='POST'>
            <label class="login_label" for="email_address">Email address</label>
            <input id="email_address" type="text" name="j_username" value=""/>

            <label class="login_label" for="password">Password</label>
            <input id="password" type="password" name="j_password"/>

            <a href="">Register new user</a>
            <a href="">Forgotten password</a>
            <input name="submit" type="submit" value="Logg inn" id="sign_in"/>
        </form>
        <div style="height: 80px"></div>
        <form method="post" id="fb-login-form" action="/fblogin">
            <a href="javascript:void(0)" id="fbLoginBtn">
                    <img src="resources/image/facebook.png">
                    <span>Sign in with Facebook</span>
            </a>
            <input type="hidden" name="authURL" value="1372966377760.2Eoy4aRS8Q6WplCWczw0M0RIMtk=">
            <input type="hidden" name="user_token" id="user_token" value="">
        </form>
        <div style="height: 30px"></div>
    </div>

</div>
<a href="social/facebook/signin">Facebook...</a>
