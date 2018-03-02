<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateSessionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create(\App\Models\Session::TABLE, function (Blueprint $table) {
            $table->increments(\App\Models\Session::ID);
            $table->dateTime(\App\Models\Session::TIME);
            $table->softDeletes();
            $table->string(\App\Models\Session::USER_ID);
              $table->ipAddress(\App\Models\Session::IP);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists(\App\Models\Reply::TABLE);
    }
}
