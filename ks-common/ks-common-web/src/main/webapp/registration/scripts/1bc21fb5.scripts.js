"use strict";angular.module("regCartApp",["configuration","ngAnimate","ngCookies","ngResource","ngSanitize","ngTouch","ui.router","ui.bootstrap"]).config(["$stateProvider","$urlRouterProvider","$httpProvider",function(a,b,c){b.otherwise("/myCart"),a.state("root.responsive",{templateUrl:"partials/responsive.html"}).state("root.responsive.schedule",{url:"/mySchedule",views:{mycart:{templateUrl:"partials/cart.html",controller:"CartCtrl"},schedule:{templateUrl:"partials/schedule.html",controller:"ScheduleCtrl"}}}).state("root.responsive.cart",{url:"/myCart",views:{mycart:{templateUrl:"partials/cart.html",controller:"CartCtrl"},schedule:{templateUrl:"partials/schedule.html",controller:"ScheduleCtrl"}}}).state("root",{templateUrl:"partials/main.html",controller:"MainCtrl"}).state("root.additionalOptions",{url:"/options",templateUrl:"partials/additionalOptions.html"}),c.interceptors.push("loginInterceptor")}]),angular.module("configuration",[]).value("APP_URL","ks-with-rice-bundled-dev/services/"),angular.module("regCartApp").constant("URLS",{scheduleOfClasses:"ScheduleOfClassesClientService",courseRegistration:"CourseRegistrationClientService",courseRegistrationCart:"CourseRegistrationCartClientService",developmentLogin:"DevelopmentLoginClientService"}).constant("STATE",function(){var a={failed:"kuali.lpr.trans.state.failed",processing:"kuali.lpr.trans.state.processing",succeeded:"kuali.lpr.trans.state.succeeded",item:{failed:"kuali.lpr.trans.item.state.failed",processing:"kuali.lpr.trans.item.state.processing",succeeded:"kuali.lpr.trans.item.state.succeeded",waitlist:"kuali.lpr.trans.item.state.waitlist",waitlistActionAvailable:"kuali.lpr.trans.item.state.waitlistActionAvailable"}};return{lpr:a,action:[a.item.waitlistActionAvailable],error:[a.failed,a.item.failed],processing:[a.processing,a.item.processing],success:[a.succeeded,a.item.succeeded],waitlist:[a.item.waitlist]}}()).constant("STATUS",{action:"action",editing:"editing",error:"error","new":"new",processing:"processing",success:"success",waitlist:"waitlist"}).constant("GRADING_OPTION",{audit:"kuali.resultComponent.grade.audit",letter:"kuali.resultComponent.grade.letter",passFail:"kuali.resultComponent.grade.passFail"}).constant("ACTION_LINK",{removeItemFromCart:"removeItemFromCart",undoDeleteCourse:"undoDeleteCourse"}),angular.module("regCartApp").controller("MainCtrl",["$scope","$rootScope","$location","$state","TermsService","ScheduleService","GlobalVarsService","APP_URL","LoginService",function(a,b,c,d,e,f,g,h,i){console.log("In Main Controller"),a.appUrl=h.replace("/services/","/"),a.$watch("termId",function(b){b&&(a.termName=e.getTermNameForTermId(a.terms,b),f.getScheduleFromServer().query({termId:b},function(b){console.log("called rest service to get schedule data - in main.js"),g.updateScheduleCounts(b),a.cartCredits=g.getCartCredits,a.cartCourseCount=g.getCartCourseCount,a.registeredCredits=g.getRegisteredCredits,a.registeredCourseCount=g.getRegisteredCourseCount,a.waitlistedCredits=g.getWaitlistedCredits,a.waitlistedCourseCount=g.getWaitlistedCourseCount,a.showWaitlistedSection=g.getShowWaitlistedSection,a.userId=g.getUserId}))}),a.terms=e.getTermsFromServer().query({termCode:null,active:!0},function(b){a.termId="kuali.atp.2012Fall",e.setTermId(a.termId),a.termName=e.getTermNameForTermId(b,a.termId)}),a.logout=function(){i.logout().query({},function(){console.log("Logging out"),location.reload()})},a.goToPage=function(a){console.log("Navigating to page: "+a),c.url(a)},a.$parent.uiState=d.current.name,a.$on("$stateChangeStart",function(b,c){a.$parent.uiState=c.name})}]),angular.module("regCartApp").controller("CartCtrl",["$scope","$modal","$timeout","STATE","STATUS","GRADING_OPTION","ACTION_LINK","CartService","ScheduleService","GlobalVarsService",function(a,b,c,d,e,f,g,h,i,j){function k(b){h.getCart().query({termId:b},function(b){a.cart=b;for(var c,f=[],g=!1,h=0;h<a.cart.items.length;h++){var i=a.cart.items[h];if(j.getCorrespondingStatusFromState(i.state)===e.processing){i.status=e.processing;var k=angular.copy(i);a.cartResults.items.push(k),a.cartResults.state=d.lpr.processing,a.cartResults.status=e.processing,g=!0,c=i.cartId}else f.push(i)}a.cart.items=f,g&&o(c)})}function l(d,g,i,j,k,l,m){h.addCourseToCart().query({cartId:d,courseCode:g,termId:i,regGroupCode:j,regGroupId:k,gradingOptionId:l,credits:m},function(b){console.log("Searched for course: "+a.courseCode+", Term: "+a.termId),a.courseCode="",a.regCode="",a.cart.items.unshift(b),console.log("Started to animate..."),b.addingNewCartItem=!0,c(function(){b.addingNewCartItem=!1},2e3)},function(c){console.log("CartId:",d),404===c.status?a.userMessage={txt:c.data,type:e.error}:400===c.status?(console.log("CartId: ",d),b.open({backdrop:"static",templateUrl:"partials/additionalOptions.html",resolve:{item:function(){return c.data},cartId:function(){return d}},controller:["$rootScope","$scope","item","cartId",function(a,b,c,d){console.log("Controller for modal... Item: ",c),b.newCartItem=c,b.newCartItem.credits=b.newCartItem.newCredits=b.newCartItem.creditOptions[0],b.newCartItem.grading=b.newCartItem.newGrading=f.letter,b.newCartItem.editing=!0,b.dismissAdditionalOptions=function(){console.log("Dismissing credits and grading"),b.$close(!0)},b.saveAdditionalOptions=function(c){c.editing=!1,console.log("Save credits and grading for cartId:",d),a.$broadcast("addCourseToCart",d,b.newCartItem.courseCode,b.newCartItem.termId,b.newCartItem.regGroupCode,b.newCartItem.regGroupId,b.newCartItem.newGrading,b.newCartItem.newCredits),b.$close(!0)}}]})):(console.log("Error with adding course",c.data.consoleMessage),a.userMessage={txt:c.data.genericMessage,type:c.data.type,detail:c.data.detailedMessage})})}function m(){if(!a.cart)return 0;for(var b=0,c=0;c<a.cart.items.length;c++)b+=Number(a.cart.items[c].credits);return b}a.statuses=e,a.oneAtATime=!1,a.isCollapsed=!0;var n=!1;a.cartResults={items:[]},a.$watch("termId",function(b){console.log("term id has changed"),a.cartResults.items.splice(0,a.cartResults.items.length),a.userMessage&&a.userMessage.txt&&a.removeUserMessage(),b&&(n=!0,k(b))}),a.$watchCollection("cart.items",function(b){a.creditTotal=m(),b&&(j.setCartCourseCount(b.length),j.setCartCredits(a.creditTotal))}),a.getStatusMessageFromStatus=function(a){var b="";return a===e.success?b=" - Success!":(a===e.error||a===e.action)&&(b=" - Failed!"),b},a.addRegGroupToCart=function(){a.courseCode=a.courseCode.toUpperCase(),l(a.cart.cartId,a.courseCode,a.termId,a.regCode,null,null,null)},a.addCartItemToCart=function(b){l(a.cart.cartId,null,a.termId,null,b.regGroupId,b.grading,b.credits)},a.$on("addCourseToCart",function(a,b,c,d,e,f,g,h){console.log("Received event addCourseToCart ",a),l(b,c,d,e,f,g,h)}),a.cancelNewCartItem=function(){a.newCartItem=null,a.showNew=!1},a.deleteCartItem=function(b){var c=a.cart.items[b],d=c.actionLinks,f=null;angular.forEach(d,function(a){a.action===g.removeItemFromCart&&(f=a.uri)}),h.removeItemFromCart(f).query({},function(d){a.cart.items.splice(b,1);var f=null;angular.forEach(d.actionLinks,function(a){a.action===g.undoDeleteCourse&&(f=a.uri)}),a.userMessage={txt:"Removed <b>"+c.courseCode+"("+c.regGroupCode+")</b>",actionLink:f,linkText:"Undo",type:e.success},a.userActionSuccessful=!0})},a.invokeActionLink=function(b){a.userActionSuccessful=!1,h.invokeActionLink(b).query({},function(b){a.cart.items.unshift(b),a.userMessage={txt:""}})},a.editCartItem=function(a){a.newCredits=a.credits,a.newGrading=a.grading,a.status=e.editing,a.editing=!0},a.updateCartItem=function(b){console.log("Updating cart item. Grading: "+b.newGrading+", credits: "+b.newCredits),h.updateCartItem().query({cartId:a.cart.cartId,cartItemId:b.cartItemId,credits:b.newCredits,gradingOptionId:b.newGrading},function(d){console.log("old: "+b.credits+" To: "+d.credits),console.log("old: "+b.grading+" To: "+d.grading);var e=b.credits,g=b.grading;b.credits=d.credits,b.grading=d.grading,b.status="",b.editing=!1,b.actionLinks=d.actionLinks,b.isopen=!b.isopen,a.creditTotal=m(),console.log("Started to animate..."),b.newGrading!==g&&(b.editGradingOption=!0,b.grading===f.letter&&(b.editGradingOptionLetter=!0),c(function(){b.editGradingOption=!1,b.editGradingOptionDone=!0},2e3),c(function(){b.editGradingOptionDone=!1,b.grading===f.letter&&(b.editGradingOptionLetter=!1)},4e3)),b.newCredits!==e&&(b.editCredits=!0,c(function(){b.editCredits=!1,b.editCreditsDone=!0},2e3),c(function(){b.editCreditsDone=!1},4e3))})},a.addCartItemToWaitlist=function(a){console.log("Adding cart item to waitlist... "),i.registerForRegistrationGroup().query({courseCode:a.courseCode,regGroupId:a.regGroupId,gradingOption:a.grading,credits:a.credits,allowWaitlist:!0},function(b){a.state=d.lpr.item.processing,a.status=e.processing,a.cartItemId=b.registrationRequestItems[0].id,c(function(){},250),console.log("Just waited 250, now start the polling"),o(b.id)})},a.removeAlertMessage=function(a){a.alertMessage=null},a.removeUserMessage=function(){a.userMessage.txt=null,a.userMessage.linkText=null},a.register=function(){h.submitCart().query({cartId:a.cart.cartId},function(b){a.userMessage={txt:""},console.log("Submitted cart. RegReqId["+b.id+"]"),a.cartResults=angular.copy(a.cart),a.cart.items.splice(0,a.cart.items.length),a.showConfirmation=!1,a.cartResults.state=d.lpr.processing,a.cartResults.status=e.processing,a.creditTotal=0,angular.forEach(a.cartResults.items,function(a){a.state=d.lpr.item.processing,a.status=e.processing}),c(function(){},250),console.log("Just waited 250, now start the polling"),o(b.id)})};var o=function(b){a.pollingCart=!1,c(function(){i.getRegistrationStatus().query({regReqId:b},function(c){a.cart.state=c.state,angular.forEach(c.responseItemResults,function(b){angular.forEach(a.cartResults.items,function(c){c.cartItemId===b.registrationRequestItemId&&(c.state=b.state,c.type=b.type,c.status=j.getCorrespondingStatusFromState(b.state),c.statusMessages=b.messages),c.status===e.processing&&(a.pollingCart=!0)})}),a.pollingCart?(console.log("Continue polling"),o(b)):(console.log("Stop polling"),a.cart.status="",a.cartResults.state=d.lpr.item.succeeded,a.cartResults.successCount=0,a.cartResults.waitlistCount=0,a.cartResults.errorCount=0,angular.forEach(a.cartResults.items,function(b){switch(b.status){case e.success:a.cartResults.successCount++;break;case e.waitlist:a.cartResults.waitlistCount++,b.waitlistMessage=j.getCorrespondingMessageFromStatus(b.status);break;case e.error:a.cartResults.errorCount++;break;case e.action:a.cartResults.errorCount++}}),i.getScheduleFromServer().query({termId:a.termId},function(b){console.log("called rest service to get schedule data - in main.js"),j.updateScheduleCounts(b),a.registeredCredits=j.getRegisteredCredits,a.registeredCourseCount=j.getRegisteredCourseCount}))})},1e3)};a.removeCartResultItem=function(b){a.cartResults.items.splice(b,1)},a.showBadge=function(a){return a.grading!==f.letter||a.editGradingOptionLetter},a.editing=function(a){return a.status===e.editing}}]);var cartServiceModule=angular.module("regCartApp");cartServiceModule.controller("ScheduleCtrl",["$scope","$modal","$timeout","STATUS","GRADING_OPTION","ScheduleService","GlobalVarsService",function(a,b,c,d,e,f,g){function h(a,b){console.log(b);var d=a.credits,f=a.gradingOptionId;a.credits=b.credits,a.gradingOptionId=b.gradingOptionId,a.editing=!1,a.isopen=!a.isopen,console.log("Started to animate..."),a.newGrading!==f&&(a.editGradingOption=!0,a.gradingOptionId===e.letter&&(a.editGradingOptionLetter=!0),c(function(){a.editGradingOption=!1,a.editGradingOptionDone=!0},2e3),c(function(){a.editGradingOptionDone=!1,a.gradingOptionId===e.letter&&(a.editGradingOptionLetter=!1)},4e3)),a.newCredits!==d&&(a.editCredits=!0,c(function(){a.editCredits=!1,a.editCreditsDone=!0},2e3),c(function(){a.editCreditsDone=!1},4e3))}a.getSchedules=g.getSchedule,a.registeredCredits=g.getRegisteredCredits,a.registeredCourseCount=g.getRegisteredCourseCount,a.waitlistedCredits=g.getWaitlistedCredits,a.waitlistedCourseCount=g.getWaitlistedCourseCount,a.numberOfDroppedWailistedCourses=0,a.userId=g.getUserId,a.$watch("termId",function(b){console.log("term id has changed: "+b),a.userMessage&&a.userMessage.txt&&a.removeUserMessage(),a.waitlistUserMessage&&a.waitlistUserMessage.txt&&a.removeWaitlistUserMessage(),f.getScheduleFromServer().query({termId:b},function(a){console.log("called rest service to get schedule data - in schedule.js"),g.updateScheduleCounts(a)})}),a.openDropConfirmation=function(b,c){console.log("Open drop confirmation"),c.dropping=!0,a.index=b,a.course=c},a.cancelDropConfirmation=function(a){a.dropping=!1},a.dropRegistrationGroup=function(b,c){console.log("Open drop confirmation for registered course"),f.dropRegistrationGroup().query({masterLprId:c.masterLprId},function(a){c.dropping=!1,c.dropProcessing=!0,i(a.id,c)},function(b){a.userMessage={txt:b.data,type:d.error}})},a.dropFromWaitlist=function(b,c){console.log("Open drop confirmation for waitlist course"),f.dropFromWaitlist().query({masterLprId:c.masterLprId},function(b){a.numberOfDroppedWailistedCourses=a.numberOfDroppedWailistedCourses+1,a.showWaitlistMessages=!0,c.dropping=!1,c.dropProcessing=!0,i(b.id,c)},function(a){c.statusMessage={txt:a.data,type:d.error}})};var i=function(a,b){console.log("start polling for course to be dropped from schedule"),b.statusMessage={txt:"<strong>"+b.courseCode+" ("+b.regGroupCode+")</strong> drop processing",type:d.processing},c(function(){f.getRegistrationStatus().query({regReqId:a},function(c){var e,f=g.getCorrespondingStatusFromState(c.state);switch(f){case d.new:case d.processing:console.log("continue polling"),i(a,b);break;case d.success:console.log("stop polling - success"),b.dropped=!0,b.dropProcessing=!1,b.waitlisted?(g.setWaitlistedCredits(parseFloat(g.getWaitlistedCredits())-parseFloat(b.credits)),g.setWaitlistedCourseCount(parseInt(g.getWaitlistedCourseCount())-1),e="Removed from waitlist for <strong>"+b.courseCode+" ("+b.regGroupCode+")</strong> successfully"):(g.setRegisteredCredits(parseFloat(g.getRegisteredCredits())-parseFloat(b.credits)),g.setRegisteredCourseCount(parseInt(g.getRegisteredCourseCount())-1),e="<strong>"+b.courseCode+" ("+b.regGroupCode+")</strong> dropped successfully"),b.statusMessage={txt:e,type:d.success};break;case d.error:console.log("stop polling - error"),b.dropProcessing=!1,e=c.responseItemResults[0].messages[0],b.statusMessage={txt:e,type:d.error}}})},1e3)};a.editScheduleItem=function(a){a.newCredits=a.credits,a.newGrading=a.gradingOptionId,a.editing=!0},a.updateScheduleItem=function(b){console.log("Updating registered course:"),console.log(b.newCredits),console.log(b.newGrading),f.updateScheduleItem().query({courseCode:b.courseCode,regGroupCode:b.regGroupCode,masterLprId:b.masterLprId,termId:a.termId,credits:b.newCredits,gradingOptionId:b.newGrading},function(a){g.setRegisteredCredits(parseFloat(g.getRegisteredCredits())-parseFloat(b.credits)+parseFloat(a.credits)),h(b,a)},function(a){b.statusMessage={txt:a.data,type:d.error}})},a.updateWaitlistItem=function(b){console.log("Updating waitlisted course:"),console.log(b.newCredits),console.log(b.newGrading),f.updateWaitlistItem().query({courseCode:b.courseCode,regGroupCode:b.regGroupCode,masterLprId:b.masterLprId,termId:a.termId,credits:b.newCredits,gradingOptionId:b.newGrading},function(a){g.setWaitlistedCredits(parseFloat(g.getWaitlistedCredits())-parseFloat(b.credits)+parseFloat(a.credits)),h(b,a)},function(a){b.statusMessage={txt:a.data,type:d.error}})},a.removeStatusMessage=function(a){a.statusMessage=null},a.removeUserMessage=function(){a.userMessage.txt=null,a.userMessage.linkText=null},a.removeWaitlistStatusMessage=function(b){b.statusMessage=null,a.numberOfDroppedWailistedCourses=a.numberOfDroppedWailistedCourses-1,0===a.numberOfDroppedWailistedCourses&&(a.showWaitlistMessages=!1)},a.showBadge=function(a){return a.gradingOptionId!==e.letter||a.editGradingOptionLetter}}]),angular.module("regCartApp").service("CartService",["ServiceUtilities","URLS",function(a,b){this.getCart=function(){return a.getData(b.courseRegistrationCart+"/searchForCart")},this.getGradingOptions=function(){return a.getData(b.courseRegistrationCart+"/getStudentRegistrationOptions")},this.addCourseToCart=function(){return a.postData(b.courseRegistrationCart+"/addCourseToCart")},this.removeItemFromCart=function(b){return a.deleteData(b)},this.invokeActionLink=function(b){return a.getData(b)},this.updateCartItem=function(){return a.putData(b.courseRegistrationCart+"/updateCartItem")},this.submitCart=function(){return a.getData(b.courseRegistrationCart+"/submitCart")},this.undoDeleteCourse=function(){return a.getData(b.courseRegistrationCart+"/undoDeleteCourse")}}]),angular.module("regCartApp").service("TermsService",["ServiceUtilities","URLS",function(a,b){var c="kuali.atp.2012Fall";this.getTermId=function(){return c},this.setTermId=function(a){c=a},this.getTermsFromServer=function(){return a.getArray(b.scheduleOfClasses+"/terms")},this.getTermNameForTermId=function(a,b){var c;return angular.forEach(a,function(a){a.termId===b&&(c=a.termName)}),c}}]),angular.module("regCartApp").service("ScheduleService",["ServiceUtilities","URLS",function(a,b){this.getScheduleFromServer=function(){return a.getData(b.courseRegistration+"/personschedule")},this.updateScheduleItem=function(){return a.putData(b.courseRegistration+"/updateScheduleItem")},this.updateWaitlistItem=function(){return a.putData(b.courseRegistration+"/updateWaitlistEntry")},this.dropRegistrationGroup=function(){return a.deleteData(b.courseRegistration+"/dropRegistrationGroup")},this.dropFromWaitlist=function(){return a.deleteData(b.courseRegistration+"/dropFromWaitlistEntry")},this.registerForRegistrationGroup=function(){return a.getData(b.courseRegistration+"/registerreggroup")},this.getRegistrationStatus=function(){return a.getData(b.courseRegistration+"/getRegistrationStatus")}}]),angular.module("regCartApp").service("LoginService",["ServiceUtilities","URLS",function(a,b){this.logOnAsAdmin=function(){return a.getData(b.developmentLogin+"/login")},this.logout=function(){return a.getData(b.developmentLogin+"/logout")}}]),angular.module("regCartApp").factory("loginInterceptor",["$q","$injector","$window",function(a,b,c){return{responseError:function(d){if(0===d.status){console.log("Failed to execute request - trying to login");var e=b.get("LoginService");e.logOnAsAdmin().query({userId:"admin",password:"admin"},function(){console.log("Logged in, reloading page."),c.location.reload()},function(){console.log("Not Logged in, reloading page."),c.location.reload()})}return a.reject(d)}}}]),angular.module("regCartApp").service("GlobalVarsService",["STATE","STATUS",function(a,b){var c,d,e,f=0,g=0,h=0,i=0,j=0;this.getCartCredits=function(){return f},this.setCartCredits=function(a){f=a},this.getCartCourseCount=function(){return g},this.setCartCourseCount=function(a){g=a},this.getRegisteredCredits=function(){return c},this.setRegisteredCredits=function(a){c=a},this.getRegisteredCourseCount=function(){return h},this.setRegisteredCourseCount=function(a){h=a},this.getWaitlistedCredits=function(){return i},this.setWaitlistedCredits=function(a){i=a},this.getWaitlistedCourseCount=function(){return j},this.setWaitlistedCourseCount=function(a){j=a},this.getSchedule=function(){return d},this.setSchedule=function(a){d=a},this.getUserId=function(){return e},this.setUserId=function(a){e=a},this.getCorrespondingStatusFromState=function(c){var d=b.new;return a.processing.indexOf(c)>=0?d=b.processing:a.success.indexOf(c)>=0?d=b.success:a.error.indexOf(c)>=0?d=b.error:a.waitlist.indexOf(c)>=0?d=b.waitlist:a.action.indexOf(c)>=0&&(d=b.action),d},this.updateScheduleCounts=function(a){var b=a.studentScheduleTermResults,c=a.userId,d=0,e=0,f=0,g=0;this.setSchedule(b),angular.forEach(b,function(a){angular.forEach(a.registeredCourseOfferings,function(a){d+=parseFloat(a.credits),e++;var b=0;angular.forEach(a.gradingOptions,function(){b++}),a.gradingOptionCount=b}),angular.forEach(a.waitlistCourseOfferings,function(a){f+=parseFloat(a.credits),g++;var b=0;angular.forEach(a.gradingOptions,function(){b++}),a.gradingOptionCount=b})}),this.setRegisteredCourseCount(e),this.setRegisteredCredits(d),this.setWaitlistedCredits(f),this.setWaitlistedCourseCount(g),this.setUserId(c)},this.getCorrespondingMessageFromStatus=function(a){var c="";return a===b.waitlist&&(c="If a seat becomes available you will be registered automatically"),c}}]),angular.module("regCartApp").service("ServiceUtilities",["$resource","APP_URL",function(a,b){function c(a){var b=[];for(var c in a)a[c]&&b.push(encodeURIComponent(c)+"="+encodeURIComponent(a[c]));return b.join("&")}this.getData=function(c){return console.log("get data function"),a(b+c,{},{query:{method:"GET",cache:!1,isArray:!1}})},this.deleteData=function(c){return console.log("delete data function"),a(b+c,{},{query:{method:"DELETE",cache:!1,isArray:!1}})},this.postData=function(d){return console.log("post data function"),a(b+d,{},{query:{headers:{"Content-Type":"application/x-www-form-urlencoded; charset=UTF-8"},method:"POST",cache:!1,isArray:!1,transformRequest:function(a){return c(a)}}})},this.putData=function(d){return console.log("put data function"),a(b+d,{},{query:{headers:{"Content-Type":"application/x-www-form-urlencoded; charset=UTF-8"},method:"PUT",cache:!1,isArray:!1,transformRequest:function(a){return c(a)}}})},this.getArray=function(c){return console.log("get data function"),a(b+c,{},{query:{method:"GET",cache:!1,isArray:!0}})}}]),angular.module("regCartApp").directive("courseOptions",function(){return{restrict:"E",transclude:!0,scope:{course:"=",maxOptions:"@max",prefix:"@",showAll:"@",moreBehavior:"@",cancelFn:"&onCancel",submitFn:"&onSubmit"},templateUrl:"partials/courseOptions.html",controller:["$scope","$modal",function(a,b){function c(a,b,c){if(a.length<=g)return!0;var d=a.indexOf(b),e=a.indexOf(c),f=2,h=Math.max(0,Math.min(d-f,a.length-g)),i=Math.min(h+g,a.length)-1;return e>=h&&i>=e}function d(){var c=a.$new();c.course=angular.copy(f),c.cancel=function(){},c.submit=function(){},f.editing=!1;var d=b.open({backdrop:"static",template:'<div class="kscr-AdditionalOptions"><course-options course="course" show-all="true" max="'+g+'" prefix="modal_'+(a.prefix?a.prefix:"")+'" on-submit="modalSubmit()" on-cancel="modalCancel()"></course-options></div>',scope:c,controller:["$scope",function(a){a.showAllCreditOptions=!0,a.showAllGradingOptions=!0,a.modalCancel=function(){a.$dismiss("cancel")},a.modalSubmit=function(){a.$close(a.course)}}]});d.result.then(function(b){f.newGrading=b.newGrading,f.newCredits=b.newCredits,a.submit()},function(){a.cancel()})}function e(){a.showAllCreditOptions=h,a.showAllGradingOptions=h}var f=a.course,g=a.maxOptions||4,h=a.showAll?!0:!1,i=a.moreBehavior||"expand";a.showAllCreditOptions=h,a.showAllGradingOptions=h,a.gradingOptions=[],f&&f.gradingOptions&&angular.forEach(f.gradingOptions,function(a,b){this.push({key:b,label:a})},a.gradingOptions),a.creditOptionsFilter=function(b){return!f||a.showAllCreditOptions?!0:c(f.creditOptions,f.credits,b)},a.gradingOptionsFilter=function(b){return!f||a.showAllGradingOptions?!0:c(Object.keys(f.gradingOptions),f.grading,b.key)},a.showMoreCreditOptions=function(){"expand"===i?a.showAllCreditOptions=!0:d()},a.showMoreGradingOptions=function(){"expand"===i?a.showAllGradingOptions=!0:d()},a.shouldShowMoreCreditOptionsToggle=function(){return!a.showAllCreditOptions&&f.creditOptions.length>g},a.shouldShowMoreGradingOptionsToggle=function(){return!a.showAllGradingOptions&&Object.keys(f.gradingOptions).length>g},a.cancel=function(){console.log("Canceling options changes"),f.newCredits=f.credits,f.newGrading=f.grading||f.gradingOptionId,f.status="",f.editing=!1,a.cancelFn&&a.cancelFn({course:f}),e()},a.submit=function(){console.log("Submitting options form"),a.submitFn&&a.submitFn({course:f}),e()},a.showGradingHelp=function(){b.open({templateUrl:"partials/gradingOptionsHelp.html"})}}]}}),angular.module("regCartApp").directive("focusMe",["$timeout","$parse",function(a,b){return{link:function(c,d,e){var f=b(e.focusMe);c.$watch(f,function(b){b===!0&&a(function(){d[0].focus()})}),d.bind("blur",function(){a(function(){d[0].focus()})})}}}]),angular.module("regCartApp").directive("sticky",["$timeout","$window","$document",function(a,b,c){return{restrict:"CA",scope:{offset:"@",minWidth:"@",maxWidth:"@"},link:function(d,e){a(function(){function f(){var a=m.outerWidth();if(k>0&&k>a||l>0&&a>l)return void(q&&(console.log("Unsticking - width constraints met: "+k+" < "+a+" < "+l),h()));var b=(m.pageYOffset||n.scrollTop())-(n.clientTop||0),c=b>=o;!q&&c?g():q&&!c?h():e.isStuck&&c}function g(){i||(i=angular.element('<div class="util-sticky-placeholder"></div>')),i.css("height",e.outerHeight()+"px"),i.insertBefore(e),e.css("top",j+"px"),e.addClass("util-sticky--stuck"),q=!0}function h(){i&&(i.remove(),i=null),e.css("top",p+"px"),e.removeClass("util-sticky--stuck"),q=!1}var i,j=d.offset||0,k=d.minWidth||-1,l=d.maxWidth||-1,m=angular.element(b),n=angular.element(c),o=e.offset().top-j,p=e.offset().top,q=!1;e.addClass("util-sticky"),m.on("scroll",f),m.on("resize",function(){a(f)}),f(),e.bind("$destroy",function(){i&&(i.remove(),i=null)})})}}}]),angular.module("regCartApp").directive("dropMenu",["$window",function(a){return{controller:["$scope",function(b){return angular.element(a).bind("resize",function(){b.dropMenu===!0&&(b.dropMenu=!1)})}]}}]);