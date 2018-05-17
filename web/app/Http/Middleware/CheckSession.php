<?php

namespace App\Http\Middleware;

use Closure;
use App\Models\Session;

class CheckSession
{
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
        if ($authResult != Session::AUTH_SUCCESS) {
            return $this->json(
                400,
                [
                    static::ERROR => $authResult
                ]
            );
        }
        return $next($request);
    }
}
