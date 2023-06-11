app.controller('productListCtrl', function ($scope, apiHandler, $rootScope) {
    $scope.query = {
        pageSize: 10,
        pageNumber: 0
    };

    $scope.dataList = [];
    $scope.pageCount = 0;
    $scope.totalCount = 0;
    $scope.category = $rootScope.Category;

    $scope.getDataList = () => {
        let url = "product/getAll/" + $scope.category.id + "?&pageSize=" + $scope.query.pageSize +
            "&pageNumber=" + $scope.query.pageNumber;
        apiHandler.callGet(url, (response) => {
            $scope.dataList = response.dataList;
            $scope.totalCount = response.totalCount;
            $scope.pageCount = $scope.totalCount / $scope.query.pageSize;
            $scope.pageCount = parseInt($scope.pageCount);
            if ($scope.totalCount % $scope.query.pageSize > 0)
                $scope.pageCount++;
        }, (error) => {

        }, true);
    };

    $scope.changePage = (pageNumber) => {
        $scope.query.pageNumber = pageNumber;
        $scope.getDataList();
    };

    $scope.range = (max) => {
        return new Array(max);
    };

    $scope.editItem = (id) => {
        $rootScope.dataId = id;
        $scope.changeMenu("product-edit");
    };

    $scope.deleteItem = (id) => {
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#0979cf',
            cancelButtonColor: '#c90449',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                apiHandler.callDelete("product/" + id, (response) => {
                    Swal.fire(
                        'Deleted!',
                        'Your data has been deleted.',
                        'success'
                    );
                    $scope.getDataList();
                }, (error) => {

                }, true);
            }
        });
    };

    $scope.changeMenuWithCategory = (template) => {
        $rootScope.Category = $scope.category;
        $scope.changeMenu(template);
    }

    $scope.getDataList();

});