/*
* Electronic Logistics Management Information System (eLMIS) is a supply chain management system for health commodities in a developing country setting.
*
* Copyright (C) 2015 Clinton Health Access Initiative (CHAI)/MoHCDGEC Tanzania.
*
* This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the GNU Affero General Public License for more details.
*/

function BarcodeActivityReportController($scope,ngTableParams,messageService,BarcodeActivityManagement,$location){


    $scope.equipmentDialogModal=false;

    // the grid options
    $scope.tableParams = new ngTableParams({
            page: 1,            // show first page
            total: 0,           // length of data
            count: 15           // count per page
     });

    $scope.OnFilterChanged = function(){
          $scope.resetRepairManagementData();
          $scope.filter.max = 10000;
          $scope.data = $scope.datarows = [];
          BarcodeActivityManagement.get($scope.filter, function(data) {
          if (data.pages !== undefined && data.pages.rows !== undefined && data.pages.rows[0]!==null) {
                     $scope.data =$scope.datarows= data.pages.rows;
                     $scope.paramsChanged($scope.tableParams);
            }

          if($scope.filter.aggregate ==='TRUE'){ $scope.aggregate=true;}
          else{ $scope.aggregate=false;}
          $scope.aggregateType = $("facility-level-filter select option:selected").html();

                });
     };

     var list=messageService.get('label.repair.management.list');
     var aggregate=messageService.get('label.repair.management.aggregate');
     $scope.types = [
             {'name': list, 'value': "FALSE"},
             {'name': aggregate, 'value': "TRUE"}
         ];



   $scope.resetRepairManagementData = function () {
        $scope.RepairManagementPieChartData = null;
        $scope.RepairManagementPieChartOption = null;
        $scope.RepairManagementNotFunctionalPieChartData=null;
        $scope.RepairManagementNotFunctionalPieChartOption=null;
        $scope.dataRows = null;
        $scope.RepairManagementRenderedData = null;
        $scope.selectedData=null;
        $scope.pieChart=false;
    };




    $scope.exportReport   = function (type){
        $scope.filter.pdformat = 1;
                  var params = jQuery.param($scope.filter);
                  var url = '/reports/download/barcode_activity_report/' + type +'?' + params;
                  window.open(url);
     };

    $scope.exportList   = function (type){
             $scope.filter.pdformat = 1;
                       var params = jQuery.param($scope.filter);
                       var url = '/reports/download/cce_repair_management_equipment_list/' + type +'?' + params;
                       window.open(url);
          };
}
