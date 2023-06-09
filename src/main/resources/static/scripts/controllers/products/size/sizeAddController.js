app.controller('sizeAddCtrl', function ($scope, apiHandler) {

    $scope.data = {};

    $scope.addData = () => {

        //check in user-interface Layer (client side).
        if ($scope.data.title === undefined || $scope.data.title == null || $scope.data.title === "") {
            Swal.fire('Please enter title!!');
            return;
        }

        apiHandler.callPost("size/", $scope.data, (response) => {
            $scope.changeMenu("size-list");
        }, (error) => {
        }, true);
    };

});