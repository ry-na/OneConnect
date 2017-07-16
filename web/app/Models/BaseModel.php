<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;


/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class BaseModel extends Authenticatable
{
    /**
     * CREATED_AT
     */
    const CREATED_AT = 'created_at';

    /**
     * ID
     */
    const ID = 'id';
}
