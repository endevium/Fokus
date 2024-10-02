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
        Schema::create('personal_access_tokens', function (Blueprint $table) {
            $table->id(); // Auto-incrementing ID
            $table->morphs('tokenable'); // Polymorphic relationship
            $table->string('name'); // Name of the token
            $table->string('token', 64)->unique(); // Unique token string
            $table->text('abilities')->nullable(); // Nullable field for token abilities
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('personal_access_tokens'); // Drop the tokens table
    }
};

