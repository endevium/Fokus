<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class TaskModel extends Model
{
    use HasFactory;

    protected $fillable = [
        'task_title',
        'fokus_app_id',

        
        
    ];

    protected $table = 'fokus_task';

    public $timestamps = false;


    
    public function fokusApp()
{
    return $this->belongsTo(FokusApp::class, 'fokus_app_id'); // foreign key relationship
}
}