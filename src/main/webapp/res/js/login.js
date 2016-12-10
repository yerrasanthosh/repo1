
$(function() {
	$('#forgot-passwd').hide();
	var username = $( "#username" ),
		password = $( "#password" ),
		company = $( "#companyCode" ),
		username0 = $( "#username0" ),
		company0 = $( "#companyCode0" ),
		allFields = $( [] ).add( username ).add( password ).add( company ).add( username0 ).add( company0 ),
		tips = $( "#msg-box" );
        errorBox = $( "#error-box" );

	function updateTips( t ) {
		tips.html( '* ' + t );
	}

	function checkLength( o, n, min, max ) {
		if ( o.val().length > max || o.val().length < min ) {
			o.addClass( "ui-state-error" );
			updateTips( "Length of " + n + " must be between " +
				min + " and " + max + "." );
			return false;
		} else {
			return true;
		}
	}

	function checkRegexp( o, regexp, n ) {
		if ( !( regexp.test( o.val() ) ) ) {
			o.addClass( "ui-state-error" );
			updateTips( n );
			return false;
		} else {
			return true;
		}
	}

	$('#btn-login').click(function(){

       	var bValid = true;
		allFields.removeClass( "ui-state-error" );
        tips.html( "" );
        errorBox.html("");

        //bValid = bValid && checkLength( username, "Username", 3, 20 );
		//bValid = bValid && checkLength( password, "Password", 5, 20 );
		//bValid = bValid && checkLength( company, "Company Code", 3, 80 );

		//bValid =  bValid && checkRegexp( username, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter." );
//        bValid =  bValid && checkRegexp( username,/^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/, "Username may consist of a-z, 0-9, underscores, begin with a letter." );
        bValid =  bValid && checkRegexp( username,/(^[\w+\-.]+@[a-z\d\-.]+\.[a-z]+$)|(^\w+[\w\d\-.@]+$)/i, "Username may consist of a-z, 0-9, underscores, begin with a letter." );
        
        bValid =  bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
        bValid =  bValid && checkRegexp( company, /^([0-9a-z_\-])+$/i, "Company Code may consist of a-z, 0-9, underscores, begin with a letter." );

        if ( bValid ) {

            //	tips.html( "" );
		//	allFields.val( "" );
           $("#loginForm").attr("action", authenticateUrl);
           $('#loginForm').submit();

        } else{
            return false;
        }

    });


	$('#btn-send').click(function(){
		var bValid = true;
		allFields.removeClass( "ui-state-error" );
        errorBox.html("");
		//bValid = bValid && checkLength( username0, "Username", 3, 20 );
		//bValid = bValid && checkLength( company0, "Company Code", 3, 80 );

        bValid =  bValid && checkRegexp( username0,/^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/, "Username may consist of a-z, 0-9, underscores, begin with a letter." );
        bValid =  bValid && checkRegexp( company0, /^([0-9a-z_\-])+$/i, "Company Code may consist of a-z, 0-9, underscores, begin with a letter." );

		if ( bValid ) {
          //  $("#forgotPasswordForm").attr("action", authenticateUrl);
              $('#forgotPasswordForm').submit();


        } else{
            return false;
        }
	});

	$('#forgot-my-password').click(function(){
		$('#login-form').hide();
		$('#forgot-passwd').show();
		tips.html( "" );
		allFields.removeClass( "ui-state-error" );
        errorBox.html("");

    });

	$('#remember-it').click(function(){
		$('#forgot-passwd').hide();
		$('#login-form').show();
		tips.html( "" );
		allFields.removeClass( "ui-state-error" );
        errorBox.html("");

    });

});