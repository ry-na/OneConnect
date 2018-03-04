<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create(\App\Models\User::TABLE, function (Blueprint $table) {
            $table->increments(\App\Models\User::ID);
            $table->timestamps();
            $table->softDeletes();
            $table->string(\App\Models\User::EMAIL)->unique();
            $table->string(\App\Models\User::PASS);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists(\App\Models\User::TABLE);
    }
}
