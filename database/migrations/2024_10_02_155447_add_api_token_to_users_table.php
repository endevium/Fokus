<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('api_token', function (Blueprint $table) {
            $table->id(); // Auto-increment
            $table->morphs('tokenable'); 
            $table->string('name'); 
            $table->string('token', 64)->unique(); 
            $table->text('abilities')->nullable(); 
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('api_token'); 
    }
};

