<?php

namespace App\Models;

/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class User extends BaseModel
{
    const TABLE = 'users';

    /**
     * EMAIL
     */
    const EMAIL = 'email';
    /**
     * PASS
     */
    const PASS = 'pass';
    /**
     * @see https://readouble.com/laravel/5.3/ja/eloquent.html
     */
    protected $fillable = [
        self::EMAIL,
        self::PASS
    ];

    /**
     * @param $request
     */
    public static function register(array $request)
    {
        static::create([
            static::EMAIL => $request[static::EMAIL],
            static::PASS => $request[static::PASS],
        ])->save();
    }

}
