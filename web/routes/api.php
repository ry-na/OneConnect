<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::group(['prefix' => \App\Http\Controllers\Api\V1\V1Controller::PREFIX], function () {
    Route::group(['prefix' => \App\Http\Controllers\Api\V1\UserController::PREFIX], function () {
        Route::post('register', 'Api\V1\UserController@register');
        Route::post('login', 'Api\V1\UserController@login');
    });
    Route::group(['middleware' => ['session']], function () {
        Route::group(['prefix' => \App\Http\Controllers\Api\V1\OpinionController::PREFIX], function () {
            Route::get('get', 'Api\V1\OpinionController@get');
            Route::get('getReply', 'Api\V1\OpinionController@getReply');
            Route::post('reply', 'Api\V1\OpinionController@reply');
            Route::post('newOpinion', 'Api\V1\OpinionController@newOpinion');
        });
    });
});
