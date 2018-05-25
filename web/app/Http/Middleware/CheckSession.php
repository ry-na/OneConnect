<?php

namespace App\Http\Middleware;

use Closure;
use App\Models\Session;
use Response;

class CheckSession
{

    const SESSION_ERROR = 'session_error';

    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request $request
     * @param  \Closure $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        $authResult = Session::Auth($request);
        if ($authResult != Session::OK) {
            return Response::json(
                [
                    static::SESSION_ERROR => $authResult
                ],
                400
            );
        }
        return $next($request);
    }
}
