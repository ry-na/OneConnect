<?php

namespace App\Models;


/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class Reply extends BaseModel
{
    const TABLE = 'replies';

    /**
     * OPINION_ID
     */
    const OPINION_ID = 'opinion_id';
    /**
     * USER_ID
     */
    const USER_ID = 'user_id';
    /**
     * REPLY_MESSAGE
     */
    const REPLY_MESSAGE = 'reply_message';

    /**
     * @see https://readouble.com/laravel/5.3/ja/eloquent.html
     */
    protected $guarded = [
        self::ID
    ];

    /**
     * Column to be obtained by Select statement.
     * @var array
     */
    public static $gettableColumns = [
        self::OPINION_ID,
        self::USER_ID,
        self::REPLY_MESSAGE,
        self::CREATED_AT
    ];
}
