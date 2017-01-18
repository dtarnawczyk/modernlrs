'use strict';
app.controller('DeleteUserConfirmDialogController', [
   '$scope', '$uibModalInstance', 'selectedCount',
   function($scope, $uibModalInstance, selectedCount) {

        $scope.selectedCount = selectedCount;
        $scope.ok = function () {
              $uibModalInstance.close();
        };

        $scope.cancel = function () {
              $uibModalInstance.dismiss('cancel')
        };
   }
]);