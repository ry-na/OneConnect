<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use Response;

/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class V1Controller extends Controller
{
    const PREFIX = 'v1';

    const ERROR = 'error';
    const ERROR_DEBUG = 'error_debug';

    /**
     * Function to create json response
     * @param int $status
     * @param null $data
     * @return mixed
     */
    protected function json($status = 200, $data = null)
    {
        return Response::json(
            $data,
            $status
        );
    }

    public function test()
    {
        return view('test');
    }
}
