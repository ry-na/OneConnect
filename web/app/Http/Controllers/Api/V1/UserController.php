<?php

namespace App\Http\Controllers\Api\V1;

use App\Models\User;
use App\Models\Session;
use Auth;
use Illuminate\Http\Request;
use Validator;

/**
 * Class providing functions related to users
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class UserController extends V1Controller
{
    const PREFIX = 'user';

    /**
     * Function to register user information
     *
     * @param Request $request
     *
     * @return mixed
     */
    public function register(Request $request)
    {
        $validator = Validator::make(
            $request->all(),
            [
                User::EMAIL => 'required|email|unique:' . User::TABLE,
                User::PASS => 'required|size:64',
            ]
        );
        if ($validator->fails()) {
            return $this->json(
                400,
                [
                    static::ERROR => $validator->errors()->all()
                ]
            );
        }
        User::register($request->all());

        return $this->json();
    }

    /**
     * Class for login processing
     *
     * @param Request $request
     *
     * @return mixed
     */
    public function login(Request $request)
    {
        $validator = Validator::make(
            $request->all(),
            [
                User::EMAIL => 'required|email',
                User::PASS => 'required|size:64',
            ]
        );
        if ($validator->fails()) {
            return $this->json(
                400,
                [
                    static::ERROR => $validator->errors()->all()
                ]
            );
        }
        $loginUser = User::all()->where(
            User::EMAIL,
            $request->{User::EMAIL}
        )->where(User::PASS, $request->{User::PASS})->first();
        if (!$loginUser) {
            return $this->json(
                400,
                [
                    static::ERROR => trans(
                        'errors.not_exist',
                        ['attribute' => 'user']
                    )
                ]
            );
        }
        Auth::login($loginUser);

        return $this->json(
            200,
            [
                [
                    Session::SESSION_ID => Session::register($loginUser, $request->ip())
                ]
            ]
        );
    }
}
