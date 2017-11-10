/*
 * Electronic Logistics Management Information System (eLMIS) is a supply chain management system for health commodities in a developing country setting.
 *
 * Copyright (C) 2015 Clinton Health Access Initiative (CHAI)/MoHCDGEC Tanzania.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the GNU Affero General Public License for more details.
 */
function GenerateQrcodeController($scope) {
      $scope.qrcodeString = "(01)"+$scope.gtinString+"(17)"+$scope.expiryDateString+"(10)"+$scope.lotNoString;
      $scope.size = 250;
      $scope.correctionLevel = '';
      $scope.typeNumber = 0;
      $scope.inputMode = '';
      $scope.image = true;
};