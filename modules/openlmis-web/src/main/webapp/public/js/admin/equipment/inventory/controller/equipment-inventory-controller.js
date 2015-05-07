/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */

function EquipmentInventoryController($scope, UserFacilityList, EquipmentInventories, ManageEquipmentInventoryProgramList, ManageEquipmentInventoryFacilityProgramList, EquipmentInventoryFacilities, EquipmentTypesByProgram, EquipmentOperationalStatus, navigateBackService, $routeParams, messageService) {

  $scope.loadFacilitiesAndPrograms = function (selectedType) {

    if (selectedType === "0") { // My facility
      // Get facility first, then programs through facility
      UserFacilityList.get({}, function (data) {
        $scope.facilities = data.facilityList;
        $scope.myFacility = data.facilityList[0];
        if ($scope.myFacility) {
          $scope.facilityDisplayName = $scope.myFacility.code + ' - ' + $scope.myFacility.name;
          ManageEquipmentInventoryFacilityProgramList.get({facilityId: $scope.myFacility.id}, function (data) {
            $scope.programs = data.programs;
          }, {});
        } else {
          $scope.facilityDisplayName = messageService.get("label.none.assigned");
          $scope.programs = undefined;
        }
      }, {});
    } else if (selectedType === "1") { // Supervised facility
      // Get programs first through supervisory nodes/requisition groups, then get facilities through programs
      ManageEquipmentInventoryProgramList.get({}, function (data) {
        $scope.programs = data.programs;
      }, {});
    }

    $scope.equipmentTypes = undefined;
    $scope.selectedEquipmentType = undefined;
  };

  $scope.loadFacilitiesAndEquipmentTypes = function () {
    $scope.loadFacilitiesForProgram();
    $scope.loadEquipmentTypesForProgram();
  };

  $scope.loadFacilitiesForProgram = function () {
    if ($scope.selectedProgram.id && $scope.selectedType === "1") {
      EquipmentInventoryFacilities.get({programId: $scope.selectedProgram.id}, function (data) {
        $scope.facilities = data.facilities;
      }, {});
    }
  };

  $scope.loadEquipmentTypesForProgram = function () {
    EquipmentTypesByProgram.get({programId: $scope.selectedProgram.id}, function (data) {
      $scope.equipmentTypes = data.equipment_types;
    }, {});
  };

  $scope.loadEquipments = function () {
    for (var i = 0; i < $scope.equipmentTypes.length; i++) {
      if ($scope.equipmentTypes[i].id.toString() === $scope.selectedEquipmentType.id) {
        $scope.selectedEquipmentType = $scope.equipmentTypes[i];
      }
    }
    if ($scope.facilities && $scope.selectedProgram && $scope.selectedEquipmentType) {
      EquipmentInventories.get({
        programId: $scope.selectedProgram.id,
        facilityId: $scope.myFacility.id
      }, function (data) {
        $scope.inventory = data.inventory;
      });
    }
  };

//  $scope.$on('$viewContentLoaded', function () {
    $scope.selectedType = $routeParams.selectedType || "0";

    /*
     $scope.$watch('programs', function () {
     if ($scope.programs && !isUndefined($routeParams.program)) {
     $scope.selectedProgram = _.where($scope.programs, {id: $routeParams.program})[0];
     }
     });
     */
    $scope.loadFacilitiesAndPrograms($scope.selectedType);
//  });

  EquipmentOperationalStatus.get(function(data){
    $scope.operationalStatusList = data.status;
  });

  $scope.getStatusName = function (statusId) {
    for (var i = 0; i < $scope.operationalStatusList.length; i++) {
      if ($scope.operationalStatusList[i].id === statusId) {
        return $scope.operationalStatusList[i].name;
      }
    }
  };

  $scope.getAge = function (yearOfInstallation) {
    return (new Date().getFullYear()) - yearOfInstallation;
  }

}